using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("api/[controller]")]
public class ProductController : ControllerBase
{
    private readonly ProductService _service;

    public ProductController(ProductService service)
    {
        _service = service;
    }

    [HttpGet("{id}")]
    public IActionResult GetProduct(int id)
    {
        var result = _service.GetProduct(id);
        return Ok(result);
    }
}