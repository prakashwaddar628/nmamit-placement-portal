import 'package:flutter/material.dart';

class ResumeScreen extends StatelessWidget {
  const ResumeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Resume')),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Icon(Icons.picture_as_pdf_outlined, size: 64, color: Colors.grey),
            const SizedBox(height: 16),
            Text(
              'Resume Upload',
              style: Theme.of(context).textTheme.headlineSmall,
            ),
            const SizedBox(height: 8),
            const Text('Mobile PDF upload under construction. Please use web portal.', textAlign: TextAlign.center),
          ],
        ),
      ),
    );
  }
}
