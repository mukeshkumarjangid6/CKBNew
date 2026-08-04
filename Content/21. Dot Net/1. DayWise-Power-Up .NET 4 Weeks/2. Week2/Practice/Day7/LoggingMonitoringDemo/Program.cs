using Microsoft.AspNetCore.HttpLogging;

var builder = WebApplication.CreateBuilder(args);

// Add Controllers
builder.Services.AddControllers();

// Swagger / OpenAPI
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

// HTTP Request/Response Logging
builder.Services.AddHttpLogging(options =>
{
    options.LoggingFields = HttpLoggingFields.All;
});

// Dependency Injection
builder.Services.AddScoped<ProductService>();
var app = builder.Build();
// Configure HTTP Request Pipeline
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI(options =>
    {
        options.SwaggerEndpoint("/swagger/v1/swagger.json", "Logging Monitoring Demo API v1");
        options.RoutePrefix = string.Empty; // Opens Swagger at root URL
    });
}
app.UseHttpsRedirection();
app.UseHttpLogging();
app.UseAuthorization();
app.MapControllers();
app.Run();