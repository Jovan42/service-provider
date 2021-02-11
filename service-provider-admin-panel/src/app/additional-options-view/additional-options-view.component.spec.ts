import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdditionalOptionsViewComponent } from './additional-options-view.component';

describe('AdditionalOptionsViewComponent', () => {
  let component: AdditionalOptionsViewComponent;
  let fixture: ComponentFixture<AdditionalOptionsViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdditionalOptionsViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdditionalOptionsViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
