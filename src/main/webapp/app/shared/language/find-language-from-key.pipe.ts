import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  standalone: true,
  name: 'findLanguageFromKey',
})
export default class FindLanguageFromKeyPipe implements PipeTransform {
  private languages: { [key: string]: { name: string; rtl?: boolean } } = {
    en: { name: 'Кыргызча' },
    fr: { name: 'Français' },
    ru: { name: 'Русский' },
    tr: { name: 'Türkçe' },
    // jhipster-needle-i18n-language-key-pipe - JHipster will add/remove languages in this object
  };

  transform(lang: string): string {
    return this.languages[lang].name;
  }
}
