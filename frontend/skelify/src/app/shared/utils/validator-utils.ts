
/****  PATTERNS  ****/
export const emailPattern = /^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]{3,}\.[a-zA-Z]{2,})$/;
export const phoneNumberPattern = /^(?:\+(?!0)|0{1,2})[0-9\s]{0,19}\d$/;

/****  FUNCTIONS  ****/
export function isValidEmail(email: string): boolean {
  return emailPattern.test(email);
}

export function isValidPhoneNumber(phoneNumber: string): boolean {
  return phoneNumberPattern.test(phoneNumber);
}

export function isNonNull<T>(value: T): value is NonNullable<T> {
  return value !== null && value !== undefined;
}

export function getLatestDate(dates: Date[]) {
  if (dates.length === 0) return new Date
  return new Date(Math.max(...dates.map(e => new Date(e).getTime())))
}
