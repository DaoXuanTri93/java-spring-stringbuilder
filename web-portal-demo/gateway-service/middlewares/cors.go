// middlewares/cors.go
package middlewares

import (
	"net/http"

	"github.com/rs/cors"
)

// CorsMiddleware trả về một handler với cấu hình CORS
func CorsMiddleware(h http.Handler) http.Handler {
	return cors.New(cors.Options{
		AllowedOrigins:   []string{"http://localhost:8080"}, // URL của client
		AllowedMethods:   []string{"GET", "POST", "PUT", "DELETE"},
		AllowedHeaders:   []string{"Content-Type", "Authorization"},
		AllowCredentials: true,
	}).Handler(h)
}
