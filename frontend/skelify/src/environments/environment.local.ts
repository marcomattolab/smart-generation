// The file contents for the current environment will overwrite these during build.
// local environment which uses `environment.ts`, but if you do
// `ng build --configuration=production` then `environment.prod.ts` will be used instead

export const environment = {
  production: false,
  clientId: 'skelify',
  apiUrl: "http://localhost:8082/api/v1",
  authority: 'http://localhost:8444/realms/skelify'
};
