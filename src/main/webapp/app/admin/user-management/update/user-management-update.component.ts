import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { LANGUAGES } from 'app/config/language.constants';
import { IUser } from '../user-management.model';
import { UserManagementService } from '../service/user-management.service';
import PasswordStrengthBarComponent from 'app/account/password/password-strength-bar/password-strength-bar.component';

const userTemplate = {} as IUser;

const newUser: IUser = {
  langKey: 'en',
  activated: true,
} as IUser;

@Component({
  standalone: true,
  selector: 'jhi-user-mgmt-update',
  templateUrl: './user-management-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule, PasswordStrengthBarComponent],
})
export default class UserManagementUpdateComponent implements OnInit {
  languages = LANGUAGES;
  authorities: string[] = [];
  isSaving = false;
  isPassValid = false;
  doNotMatch = false;
  passwordValidators = [Validators.required, Validators.minLength(4), Validators.maxLength(50)];

  editForm = new FormGroup({
    id: new FormControl(userTemplate.id),
    login: new FormControl(userTemplate.login, {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    newPassword: new FormControl(userTemplate.newPassword),
    confirmPassword: new FormControl(userTemplate.confirmPassword),
    firstName: new FormControl(userTemplate.firstName, { validators: [Validators.required, Validators.maxLength(50)] }),
    lastName: new FormControl(userTemplate.lastName, { validators: [Validators.required, Validators.maxLength(50)] }),
    email: new FormControl(userTemplate.email, {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email],
    }),
    activated: new FormControl(userTemplate.activated, { nonNullable: true }),
    langKey: new FormControl(userTemplate.langKey, { nonNullable: true }),
    authorities: new FormControl(userTemplate.authorities, { nonNullable: true }),
  });

  constructor(
    private userService: UserManagementService,
    private route: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ user }) => {
      if (user) {
        this.isPassValid = true;
        this.editForm.reset(user);
      } else {
        this.isPassValid = false;
        this.editForm.controls.newPassword.addValidators(this.passwordValidators);
        this.editForm.controls.confirmPassword.addValidators(this.passwordValidators);
        this.editForm.reset(newUser);
      }
    });
    this.userService.authorities().subscribe(authorities => (this.authorities = authorities));
  }

  previousState(): void {
    window.history.back();
  }
  isdf = false;
  changePassword(ps: string) {
    if (this.isPassValid) {
      console.log(ps);
      if (ps.length > 0) {
        this.isdf = true;
        this.editForm.controls.newPassword.addValidators(this.passwordValidators);
      } else {
        this.editForm.controls.newPassword.removeValidators(this.passwordValidators);
        this.editForm.controls.newPassword.updateValueAndValidity();
      }
    }
  }

  save(): void {
    this.isSaving = true;
    this.doNotMatch = false;

    const user = this.editForm.getRawValue();
    const newPassword = this.editForm.controls.newPassword.value;
    const confirmPassword = this.editForm.controls.confirmPassword.value;
    if (
      newPassword == null ||
      confirmPassword == null ||
      (newPassword != null && confirmPassword != null && newPassword !== confirmPassword)
    ) {
      this.doNotMatch = true;
      this.isSaving = false;
    } else {
      if (user.id !== null) {
        this.userService.update(user).subscribe({
          next: () => this.onSaveSuccess(),
          error: () => this.onSaveError(),
        });
      } else {
        this.userService.create(user).subscribe({
          next: () => this.onSaveSuccess(),
          error: () => this.onSaveError(),
        });
      }
    }
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }
}
