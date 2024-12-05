use anyhow::Error;
use rustcurr::{api::structs::ApiResponse, cli};
use serde::{Deserialize, Serialize};

/// Core application struct that holds all the data from user and API url for ExchangeRate Api communication
pub struct App {
    /// User data from CLI it holds base currency, target currency and amount
    pub user_data: cli::CliHandler,

    /// Cache file path
    pub cache_file_path: String,

    /// API url for ExchangeRate Api
    pub api_url: String,
}

/// CacheItem struct is used to cache the ExchageRate API response for a specific base currency
#[derive(Debug, Serialize, Deserialize)]
pub struct CacheItem {
    /// Base currency for the cached ExchageRate API response
    pub base: String,

    /// ExchageRate API response for the base currency
    pub api_response: ApiResponse,
}

impl CacheItem {
    /// Create a new CacheItem with default values
    pub fn new() -> Self {
        CacheItem {
            base: String::new(),
            api_response: ApiResponse::new(),
        }
    }

    /// Create a new CacheItem from a file
    pub fn from_file(file_path: &std::path::Path) -> Result<Self, Error> {
        let file = std::fs::File::open(file_path)?;
        let reader = std::io::BufReader::new(file);
        let cache_item = serde_json::from_reader(reader)?;
        Ok(cache_item)
    }
}
