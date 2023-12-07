import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChannelComponent } from './channel.component';

describe('ChannelComponent', () => {
  let component: ChannelComponent;
  let fixture: ComponentFixture<ChannelComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ChannelComponent]
    });
    fixture = TestBed.createComponent(ChannelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
