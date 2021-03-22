import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit {

  messaggio: String = 'No way!s'

  // as Java
  constructor() { }

  // prima dell'avvio del componente si avvia il metodo ngOnInit
  // Inizializza la classe
  ngOnInit(): void {
    console.log(this.messaggio);
  }

}
