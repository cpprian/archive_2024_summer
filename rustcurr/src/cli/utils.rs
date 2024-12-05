pub fn prompt(writer: &mut impl std::io::Write, message: &str) -> Result<String, anyhow::Error> {
    write!(writer, "{}", message)?;
    writer.flush()?;

    let mut input = String::new();
    std::io::stdin().read_line(&mut input)?;

    Ok(input.trim().to_string())
}
