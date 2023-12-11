import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageChannelComponent } from './manage-channel.component';

describe('ManageChannelComponent', () => {
  let component: ManageChannelComponent;
  let fixture: ComponentFixture<ManageChannelComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManageChannelComponent]
    });
    fixture = TestBed.createComponent(ManageChannelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
