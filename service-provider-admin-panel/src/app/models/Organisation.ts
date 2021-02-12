export interface Organisation {
  id?: number;
  name: string;
  email: string;
  description: string;
  address: string;
  specifications?: Specification[];
  additionalRequirements?: Specification[];
  showSpecification?: boolean;
  showAdditionalRequirements?: boolean;
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
