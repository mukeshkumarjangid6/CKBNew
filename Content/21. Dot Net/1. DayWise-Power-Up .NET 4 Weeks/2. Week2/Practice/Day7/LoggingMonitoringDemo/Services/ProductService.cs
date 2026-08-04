using Microsoft.Extensions.Logging;

public class ProductService
{
    private readonly ILogger<ProductService> _logger;
    public ProductService(ILogger<ProductService> logger)
    {
        _logger = logger;
    }
    public string GetProduct(int id)
    {
        _logger.LogInformation("Request received for Product ID: {ProductId}", id);

        if (id <= 0)
        {
            _logger.LogWarning("Invalid Product ID received: {ProductId}", id);
            return "Invalid Product ID";
        }

        try
        {
            // Simulate DB call
            if (id == 999)
                throw new Exception("Database timeout exception");

            return $"Product {id} - Name: Demo Product";
        }
        catch (Exception ex)
        {
            _logger.LogError(ex, "Failed to fetch product details.");
            return "Error fetching product";
        }
    }

}