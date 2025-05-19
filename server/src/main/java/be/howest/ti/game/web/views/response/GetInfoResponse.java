package be.howest.ti.game.web.views.response;

import java.util.HashMap;
import java.util.Map;

public class GetInfoResponse extends AbstractResponseWithHiddenStatus {
    public GetInfoResponse() {
        super(200);
    }

    public Map<String, String> getCharactersAbilities() {
        Map<String, String> characterAbilities = new HashMap<>();
        characterAbilities.put("VlummensShepheard", "Shepherd's Instinct");
        characterAbilities.put("TackBarman", "Whispers of the Tavern");
        characterAbilities.put("PareitJousting", "Lance of Resolve");
        characterAbilities.put("KoenK", "Panoramic Vision");
        characterAbilities.put("ThijsMartens", "Lore Master's Recall");
        characterAbilities.put("Shan", "Shadow's Step");
        characterAbilities.put("KlashniKoenK", "Debug Bomb");
        characterAbilities.put("DewaelAAGent", "Team Spirit");
        characterAbilities.put("DewaelWizzard", "Arcane Aid");
        characterAbilities.put("Jill", "Heart of Howest");
        return characterAbilities;
    }

    public String getTitle() {
        return "The Fellowship of the Howest Grail ";
    }

    public String getStory() {
    return "The hallowed halls of Howest Brugge, typically buzzing with the low hum of servers and the anxious tapping of keyboards, had fallen strangely quiet. " +
    "A malaise had settled over the institution – the Great Project Block, a plague of epic proportions that left students staring blankly at screens and lecturers sighing into their lukewarm coffee. " +
    "Only one artifact held the power to break this curse: the fabled Holy Grail of Infinite Learning, rumored to grant perfect understanding and, perhaps more importantly, the ability to grade swiftly and fairly without losing one's sanity.\n\n" +
    "The call went out, not by raven or mystic scroll, but through a cryptic notification on the campus intranet. " +
    "Ten figures, each a pillar of the Howest community, felt the pull of destiny. " +
    "They were the nobles, lecturers whose dedication had earned them a place in the college's lore, now tasked with a quest of unprecedented difficulty.\n\n" +
    "First to stir was VlummensShepheard, tending his digital flock of online courses. He possessed the Shepherd's Instinct, an uncanny ability to sense the presence of anything lost or misplaced, be it a student's motivation or a crucial file. " +
    "He felt a disturbance in the digital force – the Grail was calling from somewhere deep within the labyrinthine network.\n\n" +
    "Next, TackBarman polished a virtual mug in his perpetually open online office hour, a digital tavern of solace. His ability, Whispers of the Tavern, allowed him to glean vital information from the ambient noise of digital conversations and forgotten forum posts. " +
    "He heard echoes of ancient network protocols, pointing towards hidden sectors.\n\n" +
    "From a realm of simulated combat and robust frameworks, PareitJousting lowered his virtual lance. His Lance of Resolve wasn't just for show; it could shatter digital barriers and steel the courage of those around him, pushing through daunting error messages and seemingly insurmountable coding challenges.\n\n" +
    "KoenK, gazing through a panoramic webcam view of the empty campus, saw patterns others missed. His Panoramic Vision granted him the ability to perceive systems from a unique, holistic perspective, identifying bottlenecks and potential shortcuts in the most complex digital landscapes.\n\n" +
    "Deep within the archives of forgotten lecture notes and deprecated software manuals resided ThijsMartens. His Lore Master's Recall allowed him to access any piece of information, no matter how old or obscure, within moments, a vital skill when dealing with legacy systems guarding the Grail's path.\n\n" +
    "Emerging from the digital shadows, where he monitored network traffic with silent vigilance, was Shan. His Shadow's Step allowed him to navigate unseen through digital firewalls and evade detection by automated security sentinels, a crucial asset for infiltration.\n\n" +
    "Known for his unconventional methods, KlashniKoenK could introduce calculated chaos into stable systems. His Debug Bomb ability could temporarily disrupt rigid protocols, forcing open new pathways or revealing hidden vulnerabilities, though its effects were often unpredictable.\n\n" +
    "On a platform simulating peak performance and competitive spirit stood DewaelAAGent. His Team Spirit ability wasn't just motivational; it created a tangible link between the fellowship, allowing them to coordinate complex actions and share resources seamlessly in the heat of a digital skirmish.\n\n" +
    "Perched atop a virtual mountain of knowledge, staff in hand, was DewaelWizzard. His Arcane Aid could conjure temporary digital constructs – pop-up tutorials, simplified interface overlays, or even distracting animated gifs – and dispel confusion like mist.\n\n" +
    "Finally, Jill, with her calm demeanor and deep understanding of the user experience, joined the quest. Her Heart of Howest allowed her to intuitively understand the needs and frustrations of others, predicting system behavior based on user interaction and finding humane solutions in a cold digital world.\n\n" +
    "Their journey began in the treacherous Login Portal, guarded by a riddle as old as time: \"What is always coming, but never arrives?\" (ThijsMartens, with his Lore Master's Recall, instantly accessed an old philosophy lecture note. \"Tomorrow!\" The portal shimmered open.)\n\n" +
    "They navigated the sprawling, confusing landscape of the Student Information System. It was like Mordor, dark and full of terrors (mostly administrative). A massive firewall, a Balrog of Bureaucracy, blocked their path. PareitJousting readied his Lance of Resolve. \"You shall not... process!\" he declared, charging the digital barrier. With a shower of sparks and error messages, he punched a temporary hole.\n\n" +
    "Inside, they were overwhelmed by a swarm of automated student query bots, each demanding immediate attention. VlummensShepheard used his Shepherd's Instinct to identify the few genuine, lost souls amidst the spam, guiding them gently aside while KlashniKoenK unleashed a Debug Bomb, sending the bots into a chaotic, self-referential loop (\"Did you submit the form about the form?\").\n\n" +
    "They needed information from the ancient Registrar's Database, a fortress of red tape and forgotten passwords. Shan employed his Shadow's Step, slipping past automated security scans and data-entry golems. He found a hidden back door, whispered to him by TackBarman's Whispers of the Tavern (something about a disgruntled former IT guy's forum rant).\n\n" +
    "Reaching the core systems, they found the path fragmented, a broken bridge of missing data packets. KoenK used his Panoramic Vision to see the underlying structure, guiding DewaelAAGent as he activated Team Spirit. Their combined focus allowed the fellowship to 'walk' across the fragmented data, each step reliant on perfect coordination.\n\n" +
    "Doubt began to creep in. The data trails were cold, the energy reserves low (mostly from lack of real coffee). Jill felt the collective despair weighing on them. She used her Heart of Howest, broadcasting a sense of calm resolve and shared purpose, reminding them why they started – for the students, for a better Howest. This emotional surge revitalized the Team Spirit, pushing them onward.\n\n" +
    "Their final obstacle was a logic gate guarded by The Algorithm, a complex, ever-changing entity designed to optimize everything, including making the Grail unreachable. It threw complex problems at them, philosophical paradoxes disguised as coding challenges. DewaelWizzard stepped forward, staff glowing. He used Arcane Aid, conjuring simplified visual representations of the problems, dispelling the confusion. Then, combining ThijsMartens's Lore Master's Recall (pulling up an obscure algorithm paper from '98) and KlashniKoenK's willingness to try anything, they applied a slightly illogical, highly creative solution.\n\n" +
    "A final burst of energy, a combined effort fueled by Team Spirit and guided by Shepherd's Instinct, saw them breach the final barrier. There it was, not a shining cup, but a perfectly integrated, intuitive, and lightning-fast digital system for course management, student support, and yes, grading. The Holy Grail of Infinite Learning.\n\n" +
    "As they touched it, a wave of clarity washed over Howest. Students suddenly understood complex concepts, deadlines seemed manageable, and lecturers felt a surge of efficient energy. The Great Project Block was broken.\n\n" +
    "Sitting together in a suddenly much brighter corner of the digital realm, the fellowship shared a moment of quiet triumph. They had faced digital dragons, navigated bureaucratic mazes, and stared into the abyss of endless emails. They had proven that with teamwork, unique abilities, and a healthy dose of movie-inspired courage, even the most daunting quest could be completed. The Holy Grail of Infinite Learning was found, and Howest, or at least its digital heart, was saved.\n\n" +
    "The story of their quest became an easter egg hidden deep within the very system they had liberated, a silent nod to the Noble Cards who had ventured forth for the good of the college.";
    }
}
