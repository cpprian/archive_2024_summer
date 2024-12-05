use std::path::Path;

use anyhow::Error;

use crate::structs::CacheItem;

pub fn load_cache(
    cache_file_path: &str,
    writer: &mut impl std::io::Write,
) -> Result<CacheItem, Error> {
    let cache_file_path = Path::new(cache_file_path);

    if !cache_file_path.exists() {
        writeln!(writer, "Cache file not found, creating a new one")?;
        let cache_item = CacheItem::new();
        return Ok(cache_item);
    }

    let cache_item = CacheItem::from_file(cache_file_path)?;
    writeln!(writer, "Cache file loaded successfully")?;
    Ok(cache_item)
}

pub fn save_cache(cache_item: &CacheItem, cache_file_path: &str) -> Result<(), Error> {
    let cache_file_path = Path::new(cache_file_path);
    let file = std::fs::File::create(cache_file_path)?;
    let bufwriter = std::io::BufWriter::new(file);
    serde_json::to_writer(bufwriter, cache_item)?;
    Ok(())
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_load_cache() {
        let mut writer = Vec::new();
        let cache_item = load_cache("test_cache.json", &mut writer).unwrap();
        assert_eq!(cache_item.base, "");
    }

    #[test]
    fn test_save_cache() {
        let cache_item = CacheItem::new();
        let result = save_cache(&cache_item, "test_cache.json");
        assert!(result.is_ok());
    }
}
