import { Component } from '@angular/core';
import {PageTemplateComponent} from '../../../../shared/components/page-template/page-template.component';
import {AddCompanyFormComponent} from './add-company-form/add-company-form.component';

@Component({
  selector: 'app-add-company',
  imports: [
    AddCompanyFormComponent
  ],
  templateUrl: './add-company.component.html',
  styleUrl: './add-company.component.css'
})
export class AddCompanyComponent {

}
