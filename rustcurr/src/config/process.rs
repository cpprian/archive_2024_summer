use anyhow::{Context, Result};
use dotenv::dotenv;

use super::structs::ConfigData;

/// Process the config data from environment variables
///
/// # Arguments
///
/// * `writer` - The writer to write the output to the console
///
/// # Returns
///
/// The config data from the environment variables as a ConfigData struct
pub fn process_config_data(writer: &mut impl std::io::Write) -> Result<ConfigData> {
    dotenv().context("Failed to read .env file")?;
    let config = load_config_data()?;
    // TODO: message to output for a user
    writeln!(writer, "Config data loaded successfully")?;
    Ok(config)
}

/// Load the config data to the ConfigData struct
///
/// # Returns
///
/// The config data as a ConfigData struct
fn load_config_data() -> Result<ConfigData> {
    let api_url = std::env::var("API_KEY").with_context(|| "API_KEY not found in config file")?;
    let cache_file_path = std::env::var("CACHE_FILE_PATH")
        .with_context(|| "CACHE_FILE_PATH not found in config file")?;

    Ok(ConfigData::new(api_url, cache_file_path))
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_load_config_data() {
        std::env::set_var("API_KEY", "api_key");
        std::env::set_var("CACHE_FILE_PATH", "cache_file_path");
        let config = load_config_data().unwrap();
        assert_eq!(config.api_key, "api_key");
    }

    #[test]
    fn test_process_config_data() {
        std::env::set_var("API_KEY", "api_key");
        std::env::set_var("CACHE_FILE_PATH", "cache_file_path");
        let mut writer = Vec::new();
        let config = process_config_data(&mut writer).unwrap();
        assert_eq!(config.api_key, "api_key");
    }
}
