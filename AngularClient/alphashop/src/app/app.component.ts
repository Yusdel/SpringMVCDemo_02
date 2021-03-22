import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css', './home.css']
})
export class AppComponent {
  title = 'alphashop';

  saluti: String = 'Hello!';
  titolo: String = 'Choose your life!'
}
