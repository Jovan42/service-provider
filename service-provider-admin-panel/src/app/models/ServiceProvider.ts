export interface ServiceProvider {
  id?: number;
  name: string;
  email: string;
  description: string;
  address: string;
  manualApprovalRequired: boolean;
  menuParts?: MenuParts[];
}

export interface Specification {
  name: string;
  value: string;
}

export interface AdditionalRequirementsGroup {
  name: string;
  description: string;
  maxSelected: number;
  minSelected: number;
  requirements: AdditionalRequirement[];
}

export interface AdditionalRequirement {
  name: string;
  price: number;
}

export interface ServiceProviderListResponse {
  content: ServiceProvider[];
  hasContent: boolean;
  pageNumber: number;
  pageSize: number;
  sort: {
    sorted: boolean;
    unsorted: boolean;
    empty: boolean;
  };
  totalElements: number;
  totalPages: number;
}

export interface MenuParts {
  id: number;
  name: string;
  description: string;
  menuItems: MenuItem[];
}

export interface MenuItem {
  id: number;
  name: string;
  description: string;
  price: number;
  specifications?: Specification[];
  additionalRequirements?: Specification[];
  showSpecification?: boolean;
  showAdditionalRequirements?: boolean;
}
