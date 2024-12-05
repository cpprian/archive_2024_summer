use serde::{Deserialize, Serialize};
use std::{error::Error, fmt::Display};

#[derive(Debug, Serialize, Deserialize)]
pub struct ApiErrorResponse {
    pub result: String,
    #[serde(rename = "error-type")]
    pub error_type: String,
}

impl Display for ApiErrorResponse {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        write!(f, "Error: {}", self.error_type)
    }
}

impl Error for ApiErrorResponse {}

impl From<reqwest::Error> for ApiErrorResponse {
    fn from(error: reqwest::Error) -> Self {
        ApiErrorResponse {
            result: "error".to_string(),
            error_type: error.to_string(),
        }
    }
}

impl PartialEq for ApiErrorResponse {
    fn eq(&self, other: &Self) -> bool {
        self.result == other.result && self.error_type == other.error_type
    }
}
