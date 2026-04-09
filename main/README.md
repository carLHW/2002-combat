# Turn-Based Combat Arena

## What This Folder Is For

- This `main/` folder contains the project guides, UML files, and team-planning documents.
- The actual Java code is in `src/`.

## Read This First

If you are new to the project, read these in order:

1. `README.md`
2. `UML_Class_Diagram/markdown/UML-class-diagram-simplified.md`
3. `UML_Class_Diagram/UML_Diagram_Explanation.txt`
4. `UML_Class_Diagram/markdown/UML-class-diagram-implementation-focused.md`
5. `CLASS_GUIDE.md`
6. `PROJECT_STRUCTURE.md`
7. `TEAM_OWNERSHIP.md`
8. `TEAM_STARTUP.md`

## What The Project Is

- This is a Java turn-based combat game.
- The game is command-line based.

## What Is Already Decided

- The fixed game content from the assignment:
  - Warrior
  - Wizard
  - Goblin
  - Wolf
  - Basic Attack
  - Defend
  - Use Item
  - Special Skill
  - Potion
  - Power Stone
  - Smoke Bomb
  - Easy / Medium / Hard
  - backup spawn

- The main project folders:
  - `src/ui/`
  - `src/api/`
  - `src/model/`
  - `src/combatants/`
  - `src/actions/`
  - `src/effects/`
  - `src/items/`
  - `src/engine/`
  - `src/level/`

## Big Idea Of The Code

- `ui/`
  - CLI menus and player input
- `api/`
  - shared interfaces and enums
- `model/`
  - abstract/base classes and support classes
- `combatants/`
  - Warrior, Wizard, Goblin, Wolf
- `actions/`
  - attacks, defend, skill use, item action
- `effects/`
  - temporary battle effects
- `items/`
  - Potion, Power Stone, Smoke Bomb
- `engine/`
  - battle loop and battle flow
- `level/`
  - difficulty and enemy setup

## Current Repo State

- The structure is already set up.
- The CLI/setup area is the most prepared.
- Shared interfaces are already prepared.
- Abstract/base classes already have starter code.
- Several teammate areas already have partial implementations.
- Many core gameplay rules are still unfinished, so treat the repo as in-progress.

## Does Anyone Need To Finish First?

- Not completely.
- It is still a good idea for the abstract/base classes to be completed early.
- But the rest of the team can start in parallel because the starter structure already exists.

## What This Repo Still Needs

- Full gameplay implementation
- Full battle CLI during turns
- Report write-up
- Final integration and testing

## Important Docs In This Folder

- `UML_Class_Diagram/svg/UML-class-diagram-simplified.svg`
  - exported class diagram
- `UML_Class_Diagram/markdown/UML-class-diagram-simplified.md`
  - Mermaid UML source (simplified)
- `UML_Class_Diagram/markdown/UML-class-diagram-implementation-focused.md`
  - Mermaid UML source focused on team implementation work
- `UML_Class_Diagram/markdown/UML-class-diagram-full.md`
  - Mermaid UML source for the fuller main design
- `UML_Class_Diagram/UML_Diagram_Explanation.txt`
  - short UML explanation
- `CLASS_GUIDE.md`
  - explains what the classes and folders do
- `PROJECT_STRUCTURE.md`
  - explains what comes from the assignment and what is our design choice
- `TEAM_OWNERSHIP.md`
  - who is handling which files
- `TEAM_STARTUP.md`
  - git workflow and startup notes

## Important Team Rules

- Do not casually rename shared classes or shared method names.
- Work mainly in your own assigned files.
- Push small commits often.
- Treat starter code as template code unless the team agrees to simplify it.
