import { Component, Input, Output, EventEmitter, HostListener, OnInit } from '@angular/core';

@Component({
  selector: 'app-dropdown-menu',
  standalone: true,
  templateUrl: './drop-down-menu.component.html'
})
export class DropdownMenuComponent implements OnInit{
  @Input() options: Array<any> = [];
  @Input() label: string = 'Select Option';
  @Input() optionKey?: string;
  @Input() preSelectedOption?: any;
  @Output() selected = new EventEmitter<any>();
  selectedOption: any = null;

  isOpen = false;

  ngOnInit() {
    if(this.preSelectedOption) {
      this.selectedOption = this.preSelectedOption;
      this.selected.emit(this.selectedOption);
    }
  }

  toggleDropdown() {
    this.isOpen = !this.isOpen;
  }

  selectOption(option: any) {
    if(this.optionKey) {
      this.selectedOption = option[this.optionKey];
      this.selected.emit(option);
    }
    else {
      this.selectedOption = option;
      this.selected.emit(option);
    }
    this.isOpen = false;
    if(this.selectedOption === this.preSelectedOption) {
      this.unselectOption();
    }
  }

  unselectOption() {
    this.selectedOption = null;
    this.isOpen = false;
    this.selected.emit(null);
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: Event) {
    const target = event.target as HTMLElement;
    if (!target.closest('.dropdown-container')) {
      this.isOpen = false;
    }
  }
}
