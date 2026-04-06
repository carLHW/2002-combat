# Team Startup

## Where We Are Now

- The project structure is already decided.
- The UML is already prepared.
- The shared interfaces are already prepared.
- The abstract/base classes are already prepared.
- The CLI/setup flow is partially prepared.
- Most non-Team-1 feature files are starter skeletons with TODO markers.

## What This Means

- You do **not** need to fully finish first before the rest can start.
- Everyone can start working in parallel now.
- Although it hopefully the Abstract Classes can be done first, because some classes depend on it
- Each person should mainly work in their own assigned files.

## Before Coding

- Read:
  - `README.md`
  - `UML_Class_Diagram/UML_Class_Diagram_Simplified.md`
  - `UML_Class_Diagram/UML_Diagram_Explanation.txt`
  - `UML_Sequence_Diagram/UML_Sequence_Diagram.md`
  - `TEAM_OWNERSHIP.md`
- Check your assigned files before writing code.
- Do not casually rename shared classes or shared methods.
- If a file already has starter code, treat it as a template unless the team agrees to simplify it.
- If a file has a `TODO`, just fill it in directly. Java does not have a `pass` keyword like Python.

## Do Not Change These Method Names

- `BattleEngine.runBattle()`
- `Action.execute(Combatant user, ActionTarget target)`
- `Action.canExecute(Combatant user, BattleView battleView)`
- `Combatant.chooseAction(BattleView battleView)`
- `Combatant.chooseTarget(Action action, BattleView battleView, BattleContext battleContext)`
- `Item.use(AbstractPlayer user, ActionTarget target)`
- `TurnOrderStrategy.sort(List<Combatant> combatants)`

What this means:

- these method names and parameters are the connection points between different people's files
- if one person renames them, other people's code may stop matching
- you can implement the body, but try not to change the method name or parameter list unless the whole team agrees

## Git Workflow

### Before starting

```bash
git fetch origin
git checkout main
git pull origin main
git checkout -b feature/<your-branch-name>
```

### While working

```bash
git add .
git commit -m "Describe what you implemented"
git push -u origin feature/<your-branch-name>
```

### Before continuing later

```bash
git fetch origin
git checkout main
git pull origin main
git checkout feature/<your-branch-name>
git merge main
```

### When finished

- push branch
- open pull request
- merge after review/check

## Suggested Branch Names

- `feature/cli-flow`
- `feature/base-classes`
- `feature/combatants-actions`
- `feature/items-effects`
- `feature/battle-engine`