import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-add-account',
  templateUrl: './add-account.component.html',
  styleUrls: ['./add-account.component.css']
})
export class AddAccountComponent implements OnInit {
  locations : Array<any> = [{name:"location1", value:"Location1"}];
  selectedLocation:any = this.locations[0];
  constructor() { }

  ngOnInit() {
  }

}
