import { Flow } from '@vaadin/flow-frontend';
import type { Route } from '@vaadin/router';
import './common/views/main-layout';

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
    path: 'master-detail',
    component: 'master-detail-view',
    icon: 'la la-columns',
    title: 'Master-Detail',
    action: async (_context, _command) => {
      await import('./views/masterdetail/master-detail-view');
      return;
    },
  },
  {
    path: 'exercises',
    component: 'exercise-view',
    icon: 'la la-globe',
    title: 'Exercise',
    action: async (_context, _command) => {
      await import('./pages/exercises/exercise-view');
      return;
    },
  },
  {
    path: 'workouts',
    component: 'workout-view',
    icon: 'la la-globe',
    title: 'Workouts',
    action: async (_context, _command) => {
      await import('./pages/workouts/workout-view');
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
