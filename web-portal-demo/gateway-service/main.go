package main

import (
	"gateway-service/middlewares"
	"io/ioutil"
	"log"
	"net/http"

	"github.com/gorilla/mux"
)

func main() {
	r := mux.NewRouter()

	// Định nghĩa các route
	r.HandleFunc("/product", ProductServiceHandler).Methods("GET")

	// Sử dụng CORS middleware từ file middlewares/cors.go
	handler := middlewares.CorsMiddleware(r)

	// Khởi chạy server
	log.Println("API Gateway đang chạy trên cổng 8081...")
	log.Fatal(http.ListenAndServe(":8081", handler))
}

// Handler cho Service Sản phẩm
func ProductServiceHandler(w http.ResponseWriter, r *http.Request) {
	resp, err := http.Get("http://localhost:8082/api/products")
	if err != nil {
		http.Error(w, "Không thể kết nối đến service sản phẩm", http.StatusInternalServerError)
		return
	}
	defer resp.Body.Close()

	body, _ := ioutil.ReadAll(resp.Body)
	w.WriteHeader(resp.StatusCode)
	w.Write(body)
}
