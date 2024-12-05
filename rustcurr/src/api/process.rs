use reqwest::header::{ACCEPT, AUTHORIZATION, CONTENT_TYPE};

use crate::cli::CliHandler;

use super::{errors::ApiErrorResponse, structs::ApiResponse};

pub fn process_api_response(
    api_url: String,
    cli_data: &CliHandler,
) -> Result<ApiResponse, ApiErrorResponse> {
    let url = format!("{}/latest/{}", api_url, cli_data.base);

    let client = reqwest::blocking::Client::new();
    let response = client
        .get(url)
        .header(AUTHORIZATION, "Bearer 12345")
        .header(CONTENT_TYPE, "application/json")
        .header(ACCEPT, "application/json")
        .send()
        .map_err(|error| ApiErrorResponse {
            result: "error".to_string(),
            error_type: error.to_string(),
        })?;

    if response.status().is_success() {
        let data = response.json().map_err(|error| ApiErrorResponse {
            result: "error".to_string(),
            error_type: error.to_string(),
        })?;
        Ok(data)
    } else {
        let error = response.json().map_err(|error| ApiErrorResponse {
            result: "error".to_string(),
            error_type: error.to_string(),
        })?;
        Err(error)
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_process_api_response() {
        let api_url = "https://v6.exchangerate-api.com/v6/52bb87e27d6b905c2d0ee092".to_string();
        let cli_data = CliHandler {
            base: "Hello".to_string(),
            target: "PLN".to_string(),
            amount: 10.0,
            list: false,
            interactive: false,
        };

        let result = process_api_response(api_url, &cli_data);
        let expected = ApiErrorResponse {
            result: "error".to_string(),
            error_type: "unsupported-code".to_string(),
        };

        assert_eq!(result.unwrap_err(), expected);
    }

    // TODO: add test cases: invalid-key, invalid-account, quota-reached
}
