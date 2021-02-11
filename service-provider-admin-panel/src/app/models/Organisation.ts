export interface Organisation {
  id?: number;
  name: string;
  email: string;
  description: string;
  address: string;
  specifications?: Specification[];
  showSpecification?: boolean;
  showAdditionalOptions?: boolean;
}

export interface Specification {
  name: string;
  value: string;
}
