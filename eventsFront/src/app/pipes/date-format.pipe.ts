import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dateFormat'
})
export class DateFormatPipe implements PipeTransform {

  transform(value: Date): string {
    const date = new Date(value);
    return date.getDate() + '.' + (date.getMonth() + 1) + '.' + date.getFullYear() + '. '
      + ('0' + date.getHours()).slice(-2) + ':' + ('0' + date.getMinutes()).slice(-2);
  }

}
