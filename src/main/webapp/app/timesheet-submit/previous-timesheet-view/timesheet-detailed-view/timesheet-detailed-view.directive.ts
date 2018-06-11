import { Directive, ViewContainerRef, ElementRef } from '@angular/core';
@Directive({
    selector: '[expandRow]'
})
export class TimeSheetsDetailedViewDirective {
    constructor(public viewContainerRef: ViewContainerRef) {}
}
