package com.codenjoy.dojo.icancode.model.items.perks;

import com.codenjoy.dojo.icancode.model.Elements;
import com.codenjoy.dojo.icancode.model.ElementsMapper;
import com.codenjoy.dojo.icancode.services.SettingsWrapper;
import com.codenjoy.dojo.services.Dice;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class PerkUtils {

    public static Optional<Perk> random(Dice dice, boolean contest) {
        List<Elements> all = new LinkedList<>(Elements.perks());
        defaultFor(contest).forEach(perk -> all.remove(perk.getState()));
        return random(dice, all.toArray(new Elements[]{}));
    }

    private static Optional<Perk> random(Dice dice, Elements... perks) {
        int index = dice.next(perks.length);
        Elements element = perks[index];
        Perk perk = (Perk)ElementsMapper.get(element);
        return Optional.ofNullable(perk);
    }

    public static boolean isPerk(Elements element) {
        return Elements.perks().contains(element);
    }

    public static List<Perk> defaultFor(boolean contest) {
        String data = SettingsWrapper.data.defaultPerks();
        if (!data.contains(",")) {
            return Arrays.asList();
        }

        String[] split = data.split(",", -1);
        if (split.length != 2) {
            return Arrays.asList();
        }
        String perks = split[contest ? 1 : 0];
        if (StringUtils.isEmpty(perks)) {
            return Arrays.asList();
        }
        return perks.chars()
                .mapToObj(ch -> Elements.valueOf((char)ch))
                .map(element -> (Perk)ElementsMapper.get(element))
                .collect(toList());
    }
}
