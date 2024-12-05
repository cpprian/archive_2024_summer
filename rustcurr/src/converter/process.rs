use anyhow::Error;

use crate::api::structs::ApiResponse;

use super::structs::CurrencyConversion;

/// Process the conversion by getting the conversion rate from
/// the ExchangeRate API response and calculating the result
///
/// # Arguments
///
/// * `api_response` - ApiResponse from the ExchangeRate API
/// * `base_currency` - Base currency from user input
/// * `target_currency` - Target currency from user input
/// * `amount` - Amount from user input that needs to be converted
///
/// # Returns
///
/// * `CurrencyConversion` - Result of the conversion from base currency to target currency
/// based on the conversion rate and amount
///
/// # Errors
///
/// * If the target currency is not found in the conversion rates
pub fn process_conversion<'a>(
    api_response: ApiResponse,
    base_currency: &'a str,
    target_currency: &'a str,
    amount: f64,
) -> Result<CurrencyConversion<'a>, Error> {
    let mut conversion = CurrencyConversion::new(base_currency, target_currency, amount);

    let conversion_rate = match get_conversion_rate(api_response, &conversion) {
        Ok(rate) => rate,
        Err(e) => return Err(e),
    };

    conversion.result = amount * conversion_rate;

    Ok(conversion)
}

/// Get the conversion rate from the ExchangeRate API response
///
/// # Arguments
///
/// * `api_response` - ApiResponse from the ExchangeRate API
/// * `conversion` - CurrencyConversion struct that holds the target currency
///
/// # Returns
///
/// * `f64` - Conversion rate from the base currency to the target currency
///
/// # Errors
///
/// * If the target currency is not found in the conversion rates
fn get_conversion_rate(
    api_response: ApiResponse,
    conversion: &CurrencyConversion,
) -> Result<f64, Error> {
    let conversion_rate = match api_response
        .conversion_rates
        .get(&conversion.target_currency.to_string())
    {
        Some(rate) => *rate,
        None => {
            return Err(Error::msg(
                "Target currency not found in the conversion rates",
            ))
        }
    };

    Ok(conversion_rate)
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_process_conversion() {
        let mut response = ApiResponse::new();
        response.base_code = "USD".to_string();
        response.conversion_rates.insert("EUR".to_string(), 0.85);
        response.conversion_rates.insert("GBP".to_string(), 0.75);

        let conversion = process_conversion(response, "USD", "EUR", 100.0).unwrap();
        assert_eq!(conversion.result, 85.0);
    }

    #[test]
    fn test_get_conversion_rate() {
        let mut response = ApiResponse::new();
        response.base_code = "USD".to_string();
        response.conversion_rates.insert("EUR".to_string(), 0.85);
        response.conversion_rates.insert("GBP".to_string(), 0.75);

        let conversion = CurrencyConversion::new("USD", "EUR", 100.0);
        let rate = get_conversion_rate(response, &conversion).unwrap();
        assert_eq!(rate, 0.85);
    }
}
