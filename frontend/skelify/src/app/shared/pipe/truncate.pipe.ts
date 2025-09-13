import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'truncate',
})
export class TruncatePipe implements PipeTransform {
  transform(text: string, maxLength: number): string {
    if (!text) {
      return ''; // Return empty string for undefined or empty text
    }

    if (text.length > maxLength) {
      return text.substring(0, maxLength - 3) + '...';
    }

    return text;
  }
}
