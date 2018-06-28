import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'formatDate'
})
export class FormatDataPipe implements PipeTransform {
    transform(hour: string): string {
        return hour.replace(hour.charAt(hour.length - 1), '').replace('PT', '');
    }
}
