import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'phoneNumber',
})
export class PhoneNumberPipe implements PipeTransform {
  transform(value: string): string {
    if (!value) {
      return value;
    }

    // Remove non-numeric characters from the input phone number
    const numericValue = value.replace(/\D/g, '');

    // Format the phone number as "+CC NPA NXX XXXX"
    const countryCode = numericValue.slice(0, 2);
    const npa = numericValue.slice(2, 5);
    const nxx = numericValue.slice(5, 8);
    const xxxx = numericValue.slice(8);

    return `+${countryCode} ${npa} ${nxx} ${xxxx}`;
  }
}
