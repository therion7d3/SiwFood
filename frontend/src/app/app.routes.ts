import { Routes } from '@angular/router';
import { LoginComponent } from './authentication/login/login.component';
import {HomeComponent} from "./home/home.component";
import {RegisterComponent} from "./authentication/register/register.component";
import {canActivate} from "./services/auth.guard";
import {ProfileComponent} from "./profile/profile.component";
import {ImageViewComponent} from "./imageviewer/imageviewer.component";
import {ImageuploaderComponent} from "./imageuploader/imageuploader.component";
import {HomepageComponent} from "./homepage/homepage.component";
import {NuovaricettaComponent} from "./nuovaricetta/nuovaricetta.component";
import {MyrecipesComponent} from "./myrecipes/myrecipes.component";
import {RicettadetailComponent} from "./ricettadetail/ricettadetail.component";
import {ListacuochiComponent} from "./listacuochi/listacuochi.component";

const routeConfig: Routes = [
  { path: 'login', component: LoginComponent, canActivate: [canActivate]},
  { path: 'register', component: RegisterComponent, canActivate: [canActivate]},
  { path: 'myprofile', component: ProfileComponent, canActivate: [canActivate]},
  { path: 'imagev', component: ImageViewComponent, canActivate: [canActivate]},
  { path: 'imageu', component: ImageuploaderComponent, canActivate: [canActivate]},
  { path: '', component: HomepageComponent, canActivate: [canActivate]},
  { path: 'nuovaricetta', component: NuovaricettaComponent, canActivate: [canActivate]},
  { path: 'register', component: RegisterComponent, canActivate: [canActivate]},
  { path: 'myrecipes', component: MyrecipesComponent, canActivate: [canActivate]},
  { path: 'ricette/:id', component: RicettadetailComponent, canActivate: [canActivate]},
  { path: 'chef', component: ListacuochiComponent, canActivate: [canActivate]},
  { path: 'chef/:id', component: ProfileComponent, canActivate: [canActivate]},
];

export default routeConfig;
