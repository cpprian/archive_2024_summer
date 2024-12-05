use std::fmt::{self, Display, Formatter};

/// CurrencyConversion struct helps to store
/// the base currency, target currency, amount and result of the conversion
#[derive(Copy, Clone, Debug)]
pub struct CurrencyConversion<'a> {
    /// Base currency from user input
    /// e.g. USD, EUR, GBP
    pub base_currency: &'a str,

    /// Target currency from user input
    /// e.g. USD, EUR, GBP
    pub target_currency: &'a str,

    /// Amount from user input that needs to be converted
    pub amount: f64,

    /// Result of the conversion from base currency to target currency
    pub result: f64,
}

impl<'a> CurrencyConversion<'a> {
    /// Create a new CurrencyConversion with default values
    ///
    /// # Arguments
    ///
    /// * `base` - Base currency from user input
    /// * `target` - Target currency from user input
    /// * `amount` - Amount from user input that needs to be converted
    ///
    /// # Returns
    ///
    /// * `CurrencyConversion` - New CurrencyConversion struct with default value for result
    pub fn new(base: &'a str, target: &'a str, amount: f64) -> CurrencyConversion<'a> {
        CurrencyConversion {
            base_currency: base,
            target_currency: target,
            amount,
            result: 0.0,
        }
    }
}

/// Implement the Display trait for CurrencyConversion
impl<'a> Display for CurrencyConversion<'a> {
    fn fmt(&self, f: &mut Formatter<'_>) -> fmt::Result {
        write!(
            f,
            "{} {} = {} {}",
            self.amount, self.base_currency, self.result, self.target_currency
        )
    }
}
