use serde::{Deserialize, Serialize};
use std::collections::HashMap;
use std::fmt::{self, Display, Formatter};

/// Struct to hold the response from the ExchangeRate API
#[derive(Debug, Clone, Serialize, Deserialize)]
pub struct ApiResponse {
    /// Result of the API call, should be "success" or "error"
    pub result: String,

    /// Link to the ExchangeRate API documentation
    pub documentation: String,

    /// Terms of use for the ExchangeRate API
    pub terms_of_use: String,

    /// Time of the last update in Unix time
    pub time_last_update_unix: u64,

    /// Time of the last update in UTC time
    pub time_last_update_utc: String,

    /// Time of the next update in Unix time
    pub time_next_update_unix: u64,

    /// Time of the next update in UTC time
    pub time_next_update_utc: String,

    /// Base currency code from user input
    pub base_code: String,

    /// Conversion rates from the base currency to other currencies
    pub conversion_rates: HashMap<String, f64>,
}

impl ApiResponse {
    /// Create a new ApiResponse with default values
    pub fn new() -> ApiResponse {
        ApiResponse {
            result: String::new(),
            documentation: String::new(),
            terms_of_use: String::new(),
            time_last_update_unix: 0,
            time_last_update_utc: String::new(),
            time_next_update_unix: 0,
            time_next_update_utc: String::new(),
            base_code: String::new(),
            conversion_rates: HashMap::new(),
        }
    }
}

impl Default for ApiResponse {
    /// Create a new ApiResponse with default values
    fn default() -> Self {
        Self::new()
    }
}

impl Display for ApiResponse {
    /// Implement the Display trait for ApiResponse
    fn fmt(&self, f: &mut Formatter) -> fmt::Result {
        writeln!(f, "Result: {}", self.result)?;
        writeln!(f, "Documentation: {}", self.documentation)?;
        writeln!(f, "Term of Use: {}", self.terms_of_use)?;
        writeln!(f, "Time Last Update (Unix): {}", self.time_last_update_unix)?;
        writeln!(f, "Time Last Update (UTC): {}", self.time_last_update_utc)?;
        writeln!(f, "Time Next Update (Unix): {}", self.time_next_update_unix)?;
        writeln!(f, "Time Next Update (UTC): {}", self.time_next_update_utc)?;
        writeln!(f, "Base Code: {}", self.base_code)?;
        writeln!(f, "Conversion Rates: {:?}", self.conversion_rates)
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_new() {
        let response = ApiResponse::new();
        assert_eq!(response.result, "");
        assert_eq!(response.documentation, "");
        assert_eq!(response.terms_of_use, "");
        assert_eq!(response.time_last_update_unix, 0);
        assert_eq!(response.time_last_update_utc, "");
        assert_eq!(response.time_next_update_unix, 0);
        assert_eq!(response.time_next_update_utc, "");
        assert_eq!(response.base_code, "");
        assert_eq!(response.conversion_rates.len(), 0);
    }
}
