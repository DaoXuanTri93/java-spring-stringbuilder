export interface User {
  id: number | null;
  userName: string | null;
  name: string | null;
  teamId: number| null;
  teamName?: string | null;
  roleId: number| null;
  roleName?: string | null;
  description: string| null;
  createdBy?: string| null;
  createdDate?: string| null;
  updatedBy?: string| null;
  updatedDate?: string| null;
}