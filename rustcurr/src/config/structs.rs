/// Struct to hold the configuration data
///
/// # Fields
///
/// * `api_key` - The API key
#[derive(Debug)]
pub struct ConfigData {
    /// The API key to work with the ExchangeRate API
    pub api_key: String,

    /// The path to the cache file
    pub cache_file_path: String,
}

impl ConfigData {
    /// Create a new instance of the ConfigData struct
    pub fn new(api_key: String, cache_file_path: String) -> ConfigData {
        ConfigData {
            api_key,
            cache_file_path,
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_new() {
        let api = "api_key".to_string();
        let cache_file_path = "cache_file_path".to_string();
        let config = ConfigData::new(api.clone(), cache_file_path.clone());
        assert_eq!(config.api_key, api);
        assert_eq!(config.cache_file_path, cache_file_path);
    }
}
