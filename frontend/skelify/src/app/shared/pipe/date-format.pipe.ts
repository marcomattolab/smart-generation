import { DatePipe } from '@angular/common';
import { Pipe, PipeTransform } from '@angular/core';
import { AppConstants } from '../../models/constant/app-constant';

export enum DATE_FORMATS {
  DAYDATE,
  MONTHDATE,
  FULLDATE, // default
}
@Pipe({
  name: 'dateFormat',
})
export class DateFormatPipe implements PipeTransform {
  constructor(public datePipe: DatePipe) {}

  transform(value?: string, type?: DATE_FORMATS) {
    let format = AppConstants.COMMON.DEFAULT_DATE_FORMAT; // => 'dd.MM.yyyy'

    if (type == DATE_FORMATS.DAYDATE) {
      format = 'dd';
    }

    if (type == DATE_FORMATS.MONTHDATE) {
      format = 'MM.yyyy';
    }

    return this.datePipe.transform(value, format);
  }
}
