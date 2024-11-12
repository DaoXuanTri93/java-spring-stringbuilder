export interface ApiResponse<T> {
  data: T
  totalSize: number
  totalLength: number
  message: string
}
