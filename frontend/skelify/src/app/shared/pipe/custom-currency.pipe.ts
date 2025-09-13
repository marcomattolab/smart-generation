import { Pipe, PipeTransform } from '@angular/core';
import { AppConstants } from '../../models/constant/app-constant';

@Pipe({
  name: 'customCurrency',
})
export class CustomCurrencyPipe implements PipeTransform {
  transform(value: number, locale = AppConstants.COMMON.DEFAULT_DATE_LOCAL): string | null {
    if (value == null) {
      return null;
    }
    const formattedValue = new Intl.NumberFormat(locale, {
      minimumFractionDigits: 2,
      maximumFractionDigits: 2,
    }).format(value);
    return formattedValue;
  }
}
