import {patchState, signalStoreFeature, type, withMethods} from '@ngrx/signals';
import {AppState} from '../../../shared/store/models/store.models';
import {inject} from '@angular/core';
import {UserService} from '../services/user.service';
import {User} from '../models/user.model';
import {Router} from '@angular/router';

export function withUserSelector() {

  return signalStoreFeature(
    {
      state: type<AppState>(),
    },

    withMethods(({user, ...store},
                 userService = inject(UserService),
                 router = inject(Router),
    ) => ({
      async getCurrentUser() {
        try {
          const response = await userService.getCurrentUser();
          patchState(store, {user: response.body});
          if(store.currentActiveName() === ''){
            patchState(store, {currentActiveName: response.body.firstname});
          }
          if(store.currentActiveId() === ''){
            patchState(store, {currentActiveId: response.body.id});
          }
        } catch (error) {
          console.error(error);
        }
      },

      async updateUser(updatedUser: User) {
        try {
          userService.updateUser(updatedUser.id, updatedUser)
            .then(resp => {
              patchState(store, { user: updatedUser});
              router.navigate(['/settings/user/display']);
            })
        } catch (error) {
          console.error('Failed to update user:', error);
        }
      },

    }))
  )
}
