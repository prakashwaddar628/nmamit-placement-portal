import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

class AdminShell extends StatelessWidget {
  final Widget child;
  const AdminShell({super.key, required this.child});

  static const _tabs = [
    (icon: Icons.dashboard_outlined, activeIcon: Icons.dashboard, label: 'Dashboard', path: '/admin/dashboard'),
    (icon: Icons.people_outline, activeIcon: Icons.people, label: 'Students', path: '/admin/students'),
    (icon: Icons.business_outlined, activeIcon: Icons.business, label: 'Companies', path: '/admin/companies'),
    (icon: Icons.work_outline, activeIcon: Icons.work, label: 'Drives', path: '/admin/job-drives'),
    (icon: Icons.assignment_outlined, activeIcon: Icons.assignment, label: 'Applications', path: '/admin/applications'),
  ];

  int _getSelectedIndex(BuildContext context) {
    final location = GoRouterState.of(context).matchedLocation;
    for (var i = 0; i < _tabs.length; i++) {
      if (location.startsWith(_tabs[i].path)) return i;
    }
    return 0;
  }

  @override
  Widget build(BuildContext context) {
    final selectedIndex = _getSelectedIndex(context);

    return Scaffold(
      body: child,
      bottomNavigationBar: NavigationBar(
        selectedIndex: selectedIndex,
        onDestinationSelected: (i) => context.go(_tabs[i].path),
        destinations: _tabs
            .map((tab) => NavigationDestination(
                  icon: Icon(tab.icon),
                  selectedIcon: Icon(tab.activeIcon),
                  label: tab.label,
                ))
            .toList(),
      ),
    );
  }
}
