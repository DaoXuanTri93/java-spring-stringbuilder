import type { ApiResponse } from '@/response/ApiResponse'
import axios, { type AxiosResponse } from 'axios'

const apiService = {
  // Thiết lập baseURL nếu cần
  setBaseURL(baseURL: string) {
    axios.defaults.baseURL = baseURL
  },

  async getAll<T>(resource: string, params = {}): Promise<ApiResponse<T>> {
    try {
      // return await axios.get(`/api/${resource}`, { params })
      const response: AxiosResponse<ApiResponse<T>> = await axios.get(
        `/api/${resource}`,
        { params },
      )
      // Kiểm tra status code
      if (response.status === 200) {
        return {
          data: response.data.data,
          message: response.data.message,
          totalLength: response.data.totalLength,
          totalSize: response.data.totalSize
        } as ApiResponse<T>
      } else {
        throw new Error(`Unexpected status code: ${response.status}`)
      }
    } catch (error) {
      handleError(error)
      throw error // Reraise error after handling
    }
  },

  async getById<T>(
    resource: string,
    id: string | number,
  ): Promise<AxiosResponse<T>> {
    try {
      return await axios.get(`/api/${resource}/${id}`)
    } catch (error) {
      handleError(error)
      throw error
    }
  },

  async create<T>(resource: string, data: T): Promise<AxiosResponse<T>> {
    try {
      return await axios.post(`/api/${resource}`, data)
    } catch (error) {
      handleError(error)
      throw error
    }
  },

  async update<T>(
    resource: string,
    id: string | number,
    data: T,
  ): Promise<AxiosResponse<T>> {
    try {
      return await axios.put(`/api/${resource}/${id}`, data)
    } catch (error) {
      handleError(error)
      throw error
    }
  },

  async delete(
    resource: string,
    id: string | number,
  ): Promise<AxiosResponse<void>> {
    try {
      return await axios.delete(`/api/${resource}/${id}`)
    } catch (error) {
      handleError(error)
      throw error
    }
  },
}

// Hàm xử lý lỗi
function handleError(error: unknown) {
  if (axios.isAxiosError(error)) {
    console.error('API Error:', error.message)
    // Có thể thêm logic để hiển thị thông báo lỗi cho người dùng
  } else {
    console.error('Unexpected Error:', error)
  }
}

export default apiService
