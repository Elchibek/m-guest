import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import PageRibbonComponent from '../profiles/page-ribbon.component';
import MainComponent from './main.component';

@NgModule({
  imports: [SharedModule, RouterModule, PageRibbonComponent],
  declarations: [MainComponent],
})
export default class MainModule {}
