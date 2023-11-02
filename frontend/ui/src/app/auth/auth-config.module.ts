import {NgModule} from '@angular/core';
import {AuthModule, LogLevel} from 'angular-auth-oidc-client';


@NgModule({
    imports: [AuthModule.forRoot({
        config: {
            authority: 'https://dev-fdcej4517yao5gy8.uk.auth0.com',
            redirectUrl: window.location.origin,
            clientId: 'I15LdangwDLlu0Y7nouyxMpMEMCdAHhq',
            scope: 'openid profile offline_access',
            responseType: 'code',
            silentRenew: true,
            useRefreshToken: true,
            logLevel:LogLevel.Debug,
            secureRoutes: ['http://localhost:8080/'],
            customParamsAuthRequest: {
              audience: 'http://localhost:8080/'
            }
        }
      })],
    exports: [AuthModule],
})
export class AuthConfigModule {}
