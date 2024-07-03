import {ApplicationConfig} from '@angular/core';
import { provideRouter } from '@angular/router';
import routeConfig from './app.routes';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import {provideHttpClient, withFetch, withInterceptors} from "@angular/common/http";
import {outgoingTokenInterceptorInterceptor} from "./outgoing-token-interceptor.interceptor";


export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routeConfig), provideAnimationsAsync(), provideHttpClient(withFetch(),withInterceptors([outgoingTokenInterceptorInterceptor]))]
};
