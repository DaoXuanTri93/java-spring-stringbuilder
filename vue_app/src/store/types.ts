export interface Product {
  id: number
  name: string
  price: number
}

export interface Search {
  id: string
  name: string
}

export interface StateGlobal {
  Message: string
}

export interface Teams {
  teamId?: string
  teamName: string
  description: string
}

export interface SearchTeams {
  search?: string
  pageNumber: string
  pageSize: string
}
