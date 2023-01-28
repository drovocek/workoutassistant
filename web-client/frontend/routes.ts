import { Flow } from '@vaadin/flow-frontend';
import type { Route } from '@vaadin/router';
import './main-layout';

const { serverSideRoutes } = new Flow({
  imports: () => import('../build/frontend/generated-flow-imports'),
});

export type ViewRoute = Route & {
  title?: string;
  icon?: string;
  children?: ViewRoute[];
};

export const views: ViewRoute[] = [
  // place routes below (more info https://hilla.dev/docs/routing)
  {
    path: '',
    component: 'workout-view',
    icon: 'la la-calendar',
    title: 'Workout',
    action: async (_context, _command) => {
      await import('./views/workouts/workout-view');
      return;
    },
  },
  {
    path: 'rounds',
    component: 'round-view',
    icon: 'la la-globe',
    title: 'Rounds',
    action: async (_context, _command) => {
      await import('./views/rounds/round-view');
      return;
    },
  },
  {
    path: 'exercises',
    component: 'exercise-view',
    icon: 'la la-dumbbell',
    title: 'Exercise',
    action: async (_context, _command) => {
      await import('./views/exercises/exercise-view');
      return;
    },
  },
];
export const routes: ViewRoute[] = [
  {
    path: '',
    component: 'main-layout',
    children: [
      ...views,
      // for server-side, the next magic line sends all unmatched routes:
      ...serverSideRoutes, // IMPORTANT: this must be the last entry in the array
    ],
  },
];
