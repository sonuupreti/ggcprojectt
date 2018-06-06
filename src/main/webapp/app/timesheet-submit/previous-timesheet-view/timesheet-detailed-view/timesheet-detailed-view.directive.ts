import { Directive, ViewContainerRef, ElementRef } from '@angular/core';
@Directive({
    selector: '[expand-row]'
})
export class timeSheetsDetailedViewDirective {
    constructor(public viewContainerRef: ViewContainerRef) {}
}
