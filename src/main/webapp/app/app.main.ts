import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { ProdConfig } from './blocks/config/prod.config';
import { Itrack2AppModule } from './app.module';
import 'hammerjs';

ProdConfig();

if (module['hot']) {
    module['hot'].accept();
}

platformBrowserDynamic()
    .bootstrapModule(Itrack2AppModule)
    .then(success => console.log(`Application started`))
    .catch(err => console.error(err));
