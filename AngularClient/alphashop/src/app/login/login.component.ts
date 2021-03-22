import { Component, OnInit } from '@angular/core';

/*
 * {{}} = Data Binding Unidirezionale dal TS al View
 * 
 * [(ngModel)] = <param-name> - Data Binding Bi-Direzionale
 * 
 * (click) = Data Binding Uni-Direzionali da View a TS
 * */
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  userid: String = 'Yusdel';
  psw: String = '';
  auth: boolean = false;
  err: String = 'Spiacente User o Psw Corretti!'
  constructor() { }

  ngOnInit(): void {
  }

  gestAut() {
    if (this.userid === 'Yusdel' && this.psw === '123')
      this.auth = true;
    else
      this.auth = false;
  }
}
